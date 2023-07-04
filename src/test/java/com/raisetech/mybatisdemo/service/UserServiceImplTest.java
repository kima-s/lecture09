package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.exception.ResourceNotFoundException;
import com.raisetech.mybatisdemo.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserMapper userMapper;

    @Test
    public void IDに紐づくユーザーが1件取得できること() throws Exception {
        doReturn(Optional.of(new User("Takeshi Sato","tokyo",35))).when(userMapper).findById(1);

        User actual = userServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new User("Takeshi Sato","tokyo",35));
        verify(userMapper, times(1)).findById(1);
    }

    @Test
    public void 存在するユーザー情報を全て返すこと() throws Exception{
        doReturn(List.of(new User("Takeshi Sato","Tokyo",35),new User("Jhon","Nagoya",22))).when(userMapper).findAll();

        List<User> actual = userServiceImpl.findAll();
        assertThat(actual).isEqualTo(List.of(new User("Takeshi Sato","Tokyo",35),new User("Jhon","Nagoya",22)));
    }

    @Test
    public void 存在しないユーザーのIDを指定したときに例外が返されること() throws Exception {
        doReturn(Optional.empty()).when(userMapper).findById(2);

        assertThatThrownBy(
                () -> userServiceImpl.findById(2)
        ).isInstanceOfSatisfying(
                ResourceNotFoundException.class, e -> assertThat(e.getMessage()).isEqualTo("resource not found")
        );
    }

    @Test
    public void 正常にユーザーが登録されること() throws Exception {
        User user = new User("Takeshi Sato","tokyo",35);
        doReturn(user).when(userMapper).createUser(user);

        User actual = userServiceImpl.createUser("Takeshi Sato","tokyo",35);
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void 存在するユーザーのIDを指定したときにユーザーを更新できること() throws Exception{
        User updateUser = new User("Satoru Hara","Akita",55);
        updateUser.setId(1);
        doReturn(Optional.of(new User("Jack","Okinawa",14))).when(userMapper).findById(1);

        userServiceImpl.updateUser(updateUser);
        verify(userMapper, times(1)).findById(1);
        verify(userMapper, times(1)).updateUser(updateUser);
    }

    @Test
    public void 存在しないユーザーのIDを指定したときにユーザーを更新せず例外が返ること() throws Exception{
        User updateUser = new User("Satoru Hara","Akita",55);
        updateUser.setId(1);
        doReturn(Optional.empty()).when(userMapper).findById(1);

        assertThatThrownBy(
                () -> userServiceImpl.updateUser(updateUser)
        ).isInstanceOfSatisfying(
                ResourceNotFoundException.class, e -> assertThat(e.getMessage()).isEqualTo("resource not found")
        );
        verify(userMapper, times(0)).updateUser(updateUser);
    }

    @Test
    public void 存在するユーザーのIDを指定したときにユーザーを削除できること() throws Exception{
        doReturn(Optional.of(new User("Jack","Okinawa",14))).when(userMapper).findById(1);

        userServiceImpl.deleteUser(1);
        verify(userMapper, times(1)).findById(1);
        verify(userMapper, times(1)).deleteUser(1);
    }

    @Test
    public void 存在しないユーザーのIDを指定したときにユーザーを削除せず例外が返ること() throws Exception{
        doReturn(Optional.empty()).when(userMapper).findById(1);

        assertThatThrownBy(
                () -> userServiceImpl.deleteUser(1)
        ).isInstanceOfSatisfying(
                ResourceNotFoundException.class, e -> assertThat(e.getMessage()).isEqualTo("resource not found")
        );
        verify(userMapper, times(0)).deleteUser(1);
    }
}
