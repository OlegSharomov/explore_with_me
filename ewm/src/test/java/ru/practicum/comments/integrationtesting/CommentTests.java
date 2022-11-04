//package ru.practicum.comments.integrationtesting;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.transaction.annotation.Transactional;
//import ru.practicum.comments.controller.admin.CommentAdminController;
//import ru.practicum.comments.controller.priv.CommentPrivateController;
//import ru.practicum.comments.controller.publ.CommentPublicController;
//
//@Transactional
//@Rollback(false)
//@TestPropertySource(properties = { "spring.datasource.url=jdbc:postgresql://localhost:5432/test"})
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@SpringJUnitConfig( {CommentAdminController.class, CommentPrivateController.class, CommentPublicController.class})
//
//public class CommentTests {
//
//
//}
