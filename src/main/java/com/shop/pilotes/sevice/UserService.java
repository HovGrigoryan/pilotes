package com.shop.pilotes.sevice;

import com.shop.pilotes.dto.response.UserResponseDto;
import com.shop.pilotes.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponseDto save(User user);

    UserResponseDto findUser(Long id);

    List<User> findUserByFirstNameContains(String like);

}
