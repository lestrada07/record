package com.nisum.api.record.service;

import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

import com.nisum.api.record.transfer.MessageModel;
import com.nisum.api.record.transfer.RecordInputModel;
import com.nisum.api.record.transfer.RecordOutputModel;
import com.nisum.api.record.transfer.ResponseModel;
import com.nisum.api.record.model.*;
import com.nisum.api.record.repository.*;
import com.nisum.api.record.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PhoneRepository phoneRepository;

    private String uuid;

    public UserService(){

    }

    public ResponseModel recordUser(RecordInputModel request){
        logger.debug("UserService.recordUser: ");
        ResponseModel response = new ResponseModel();
        Properties prop = new Properties();
        InputStream input;
        MessageModel messageModel = new MessageModel();
        UserEntity user;
        EmailEntity email;
        CityEntity city;
        PhoneEntity phone;
        RecordOutputModel recordOutput = new RecordOutputModel();
        String formatEmail;
        String formatPassword;
        String phoneNotNumeric;
        String emailRegister = "";
        String phoneCode;
        String countryCode;
        String jwtToken;
        String active;
        String userRecord;
        String statusOk;
        String statusError;
        String statusException = "";
        String statusMessageOk;
        String statusMessageError;
        String statusMessageException = "";
        String incompleteParameters;
        boolean isNumericCountry;
        boolean isNumericCity;
        boolean isNumericNumber;
        boolean notNumeric = false;
        boolean phoneCodeNotRegister = false;
        boolean validateMail;
        boolean validatePassword;

        try {

            input = UserService.class.getClassLoader().getResourceAsStream("config.properties");

            prop.load(input);

            statusOk = prop.getProperty("status.ok");
            statusError = prop.getProperty("status.error");
            statusException = prop.getProperty("status.exception");

            statusMessageOk = prop.getProperty("status.message.ok");
            statusMessageError = prop.getProperty("status.message.error");
            statusMessageException = prop.getProperty("status.message.exception");

            //Validate Parameters, if they are complete, the process continues, else an error message will be answered
            if(request.getName() != null && !request.getName().equals("")
                    && request.getPassword() != null && !request.getPassword().equals("")
                    && request.getEmail() != null && !request.getEmail().equals("")){

                logger.debug("Record User name: " + request.getName());
                logger.debug("Record User email: " + request.getEmail());

                //Validate the mail format
                validateMail=Utility.validateMail(request.getEmail());

                //If the email is valid continue, else an error message will be answered
                if(validateMail){

                    //Validate if all phone fields are numeric//
                    for(int i=0; i<request.getPhones().size(); i++){
                        isNumericCountry = Utility.isNumeric(request.getPhones().get(i).getContrycode());
                        isNumericCity = Utility.isNumeric(request.getPhones().get(i).getCitycode());
                        isNumericNumber = Utility.isNumeric(request.getPhones().get(i).getNumber());

                        if (!isNumericCountry || !isNumericCity || !isNumericNumber){
                            notNumeric = true;
                        }
                    }

                    //If any data is not numeric, it will respond with an error message, else it will continue the process
                    if(notNumeric){
                        logger.error("Phone not numeric");
                        phoneNotNumeric = prop.getProperty("message.error.phone.not.numeric");
                        messageModel.setMessage(phoneNotNumeric);
                        response.setStatus(statusError);
                        response.setStatusMessage(statusMessageError);
                        response.setData(messageModel);
                    }else{

                        //Validate if the email exists
                        email = emailRepository.findByEmailAddress(request.getEmail());

                        //If the email exists, it will respond with an error message, if it does not continue with the process
                        if(email != null) {
                            logger.error("Email exist");
                            emailRegister = prop.getProperty("message.error.email.register");
                            messageModel.setMessage(emailRegister);
                            response.setStatus(statusError);
                            response.setStatusMessage(statusMessageError);
                            response.setData(messageModel);
                        } else {

                            //Generate UUID, if the identifier exists, a new one will be generated
                            do{
                                //Generate UUID for the user
                                setUuid(Utility.generateUUID());

                                //Validate that UUID is unique
                                user = userRepository.findByUIID(getUuid());
                            }while (user != null);

                            //Validate that all country and city codes are registered
                            for(int i=0; i<request.getPhones().size(); i++){

                                countryCode = countryRepository.findByPhoneCode(Integer.parseInt(request.getPhones().get(i).getContrycode()));

                                if(countryCode != null){
                                    city = cityRepository.findByPhoneCodeAndCountryCode(Integer.parseInt(request.getPhones().get(i).getCitycode()), countryCode);

                                    if(city != null){
                                        logger.debug("Phone codes exist");
                                        logger.debug("Phone code country: " + request.getPhones().get(i).getContrycode());
                                        logger.debug("Phone code city: " + request.getPhones().get(i).getCitycode());
                                    }else{
                                        logger.error("Phone code city not exist: " + request.getPhones().get(i).getCitycode());
                                        phoneCodeNotRegister = true;
                                    }
                                }else{
                                    logger.error("Phone code country not exist: " + request.getPhones().get(i).getContrycode());
                                    phoneCodeNotRegister = true;
                                }
                            }

                            //If the country or city code does not exist, it will respond with an error message, otherwise the process continues
                            if(phoneCodeNotRegister){
                                logger.error("Phone code country or city are invalid");
                                phoneCode = prop.getProperty("message.error.phone.code");
                                messageModel.setMessage(phoneCode);
                                response.setStatus(statusError);
                                response.setStatusMessage(statusMessageError);
                                response.setData(messageModel);
                            }else{

                                //Validate the format of the send password
                                validatePassword = Utility.validatePassword(request.getPassword());

                                //If the format meets the requirements, the process continues, if not an error message is sent
                                if(validatePassword){

                                    //Generate Token
                                    jwtToken = Utility.generateToken(request.getName(), request.getEmail(), uuid);

                                    active = prop.getProperty("active");

                                    //Set variables to the user entity
                                    user = new UserEntity();
                                    user.setUserId(getUuid());
                                    user.setName(request.getName());
                                    user.setPassword(Utility.encrypt(request.getPassword()));
                                    user.setToken(jwtToken);
                                    user.setCreated(Date.from(Instant.now()));
                                    user.setModified(Date.from(Instant.now()));
                                    user.setLastLogin(Date.from(Instant.now()));
                                    user.setIsActive(Integer.parseInt(active));

                                    //Save to user repository
                                    userRepository.saveAndFlush(user);


                                    //Set variables to the email entity
                                    email = new EmailEntity();
                                    email.setUserId(getUuid());
                                    email.setEmailAddress(request.getEmail());

                                    //Save to email repository
                                    emailRepository.saveAndFlush(email);

                                    //Set variables to the phone entity and save, loop through the list
                                    for(int i=0; i<request.getPhones().size(); i++){
                                        phone = new PhoneEntity();
                                        phone.setUserId(getUuid());
                                        countryCode = countryRepository.findByPhoneCode(Integer.parseInt(request.getPhones().get(i).getContrycode()));
                                        city = cityRepository.findByPhoneCodeAndCountryCode(Integer.parseInt(request.getPhones().get(i).getCitycode()), countryCode);
                                        phone.setCityCode(city.getCityCode());
                                        phone.setPhoneNumber(Integer.parseInt(request.getPhones().get(i).getNumber()));
                                        phoneRepository.saveAndFlush(phone);
                                    }

                                    //Search for the saved record by UUID
                                    user = userRepository.findByUIID(getUuid());

                                    //If it exists, the values are assigned to the response, and it is sent, if not, an error message is sent
                                    if(user != null){
                                        recordOutput.setId(user.getUserId());
                                        recordOutput.setToken(user.getToken());
                                        recordOutput.setCreated(user.getCreated().toString());
                                        recordOutput.setModified(user.getModified().toString());
                                        recordOutput.setLast_login(user.getLastLogin().toString());
                                        recordOutput.setIsactive(String.valueOf(user.getIsActive()));

                                        logger.debug("Successful process");
                                        response.setStatus(statusOk);
                                        response.setStatusMessage(statusMessageOk);
                                        response.setData(recordOutput);
                                    }else{
                                        logger.error("Error in user register");
                                        userRecord = prop.getProperty("message.error.user.record");
                                        messageModel.setMessage(userRecord);
                                        response.setStatus(statusError);
                                        response.setStatusMessage(statusMessageError);
                                        response.setData(messageModel);
                                    }

                                }else{
                                    logger.error("Password format invalid");
                                    formatPassword = prop.getProperty("message.error.format.password");
                                    messageModel.setMessage(formatPassword);
                                    response.setStatus(statusError);
                                    response.setStatusMessage(statusMessageError);
                                    response.setData(messageModel);
                                }
                            }
                        }
                    }
                }else{
                    logger.error("Email format invalid");
                    formatEmail = prop.getProperty("message.error.format.email");
                    messageModel.setMessage(formatEmail);
                    response.setStatus(statusError);
                    response.setStatusMessage(statusMessageError);
                    response.setData(messageModel);
                }
            }else {
                logger.error("Incomplete parameters");
                incompleteParameters = prop.getProperty("message.error.parameter");
                messageModel.setMessage(incompleteParameters);
                response.setStatus(statusError);
                response.setStatusMessage(statusMessageError);
                response.setData(messageModel);
            }
            return response;
        }catch (Exception e){
            logger.error("Exception error");
            logger.error(e.getMessage());
            response.setStatus(statusException);
            response.setStatusMessage(statusMessageException);
            response.setData(null);
            return response;
        }finally {
            logger.debug("Ended process");
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

