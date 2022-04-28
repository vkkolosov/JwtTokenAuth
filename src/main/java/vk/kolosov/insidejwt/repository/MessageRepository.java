package vk.kolosov.insidejwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.kolosov.insidejwt.model.Message;
import vk.kolosov.insidejwt.model.UserInfo;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUserId(Integer id);

}
