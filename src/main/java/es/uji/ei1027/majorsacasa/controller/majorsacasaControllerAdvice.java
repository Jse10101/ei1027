package es.uji.ei1027.majorsacasa.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class majorsacasaControllerAdvice {

    @ExceptionHandler(value = majorsacasaException.class)
    public ModelAndView handleClubException(majorsacasaException ex){

        ModelAndView mav = new ModelAndView("error/exceptionError");
        mav.addObject("message", ex.getMessage());
        mav.addObject("errorName", ex.getErrorName());
        return mav;
    }

}