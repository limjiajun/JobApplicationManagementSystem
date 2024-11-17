// package net.javaguides.springboot.config;

// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.servlet.NoHandlerFoundException;

// @ControllerAdvice
// public class ErrorHandler {

//     @ExceptionHandler(NoHandlerFoundException.class)
//     public String handleNotFoundError(NoHandlerFoundException e, Model model) {
//         model.addAttribute("errorMessage", "The page you are looking for was not found.");
//         return "error"; // 定向到 error.html 页面
//     }
// }
