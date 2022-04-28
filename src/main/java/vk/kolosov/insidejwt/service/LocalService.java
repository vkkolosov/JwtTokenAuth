package vk.kolosov.insidejwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vk.kolosov.insidejwt.model.Message;
import vk.kolosov.insidejwt.model.UserInfo;
import vk.kolosov.insidejwt.repository.MessageRepository;
import vk.kolosov.insidejwt.repository.UserInfoRepository;
import vk.kolosov.insidejwt.security.jwt.JwtAuthenticationException;

import java.util.List;

@Service
public class LocalService {

    private final UserInfoRepository userInfoRepository;

    private final MessageRepository messageRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LocalService(UserInfoRepository userInfoRepository, MessageRepository messageRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //UserInfoService

    public void register(UserInfo userInfo) {

        if(userInfoRepository.findUserInfoByName(userInfo.getName()) != null) {
            throw new JwtAuthenticationException("User already exist");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);

    }


    public UserInfo findUserInfoByName(String name) {
        UserInfo userInfo = userInfoRepository.findUserInfoByName(name);
        userInfo.setMessageList(messageRepository.findAllByUserId(userInfo.getId()));
        return userInfo;
    }

    public void saveUserInfo(UserInfo userInfo) {
        if (userInfoRepository.findUserInfoByName(userInfo.getName()) != null) {
            userInfoRepository.save(userInfo);
        }
    }

    //MessageService

    public List<Message> findAllMessagesByUserInfoName(String name) {
        UserInfo userInfo = findUserInfoByName(name);
        return messageRepository.findAllByUserId(userInfo.getId());
    }

    public void saveMessage(String name, String message) {
        UserInfo userInfo = userInfoRepository.findUserInfoByName(name);
        Message message1 = new Message();
        message1.setUserId(userInfo.getId());
        message1.setMessageText(message);
        messageRepository.save(message1);
    }

}
