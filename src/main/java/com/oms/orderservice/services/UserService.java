package com.oms.orderservice.services;

import com.oms.orderservice.commondto.UserRequestDTO;
import com.oms.orderservice.commondto.UserResponseDTO;
import com.oms.orderservice.dto.UpdateUserRequestDTO;
import com.oms.orderservice.entity.Users;
import com.oms.orderservice.mapper.UserMapper;
import com.oms.orderservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        final String methodName = "createUser";
        logger.info("Entry", methodName);
        Users saveUser = buildSaveUserRequest(requestDTO);
        UserResponseDTO createUserResponse = new UserResponseDTO();

        createUserResponse.setUserId(saveUser.getUserId());
        createUserResponse.setFirstName(saveUser.getFirstName());
        createUserResponse.setLastName(saveUser.getLastName());
        createUserResponse.setEmailId(saveUser.getEmailId());
        createUserResponse.setCreatedAt(saveUser.getCreatedAt());
        createUserResponse.setUserCreatedMessage("User Created Successfully");

        logger.info("Exit", methodName);

        return createUserResponse;
    }

    @Transactional
    private Users buildSaveUserRequest(UserRequestDTO userRequest) {
        final String methodName = "buildSaveUserRequest";
        logger.info("Entry {}", methodName);
        logger.info("Save User Request : {}", userRequest);
        Users saveUser = new Users();

        saveUser.setUserId(userRequest.getUserId());
        saveUser.setFirstName(userRequest.getFirstName());
        saveUser.setLastName(userRequest.getLastName());
        saveUser.setGender(userRequest.getGender());
        saveUser.setEmailId(userRequest.getEmailId());
        saveUser.setPhoneNumber(userRequest.getPhoneNumber());
        saveUser.setDateOfBirth(userRequest.getDateOfBirth());
        saveUser.setPassword(encryptPassword(userRequest.getPassword()));
        saveUser.setActive(true);
        saveUser.setAddressLine1(userRequest.getAddressLine1());
        saveUser.setCity(userRequest.getCity());
        saveUser.setCountry(userRequest.getCountry());

        userRepository.save(saveUser);
        logger.info("Exit {}", methodName);
        return saveUser;
    }

    public Users updateUser(UpdateUserRequestDTO updateUserRequest) {
        final String methodName = "updateUser";
        logger.info("Entry {}", methodName);
        Users updateUser = userRepository.findById(updateUserRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found for UserId"));

        userMapper.updateUserMapper(updateUserRequest, updateUser);

        logger.info("Exit {}", methodName);
        return userRepository.save(updateUser);
    }

    private String encryptPassword(String password) {
        StringBuilder updatedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            updatedPassword.append("X");
        }

        return updatedPassword.toString();
    }
    public Users getUserById(String userId){
        return userRepository.getUserByUserId(userId);
    }
    public boolean validateUser(String userId){
        Users getUserResponse = userRepository.getUserByUserId(userId);
        if (getUserResponse != null && getUserResponse.isActive()){
            return true;
        }else
        return false;
    }
}
