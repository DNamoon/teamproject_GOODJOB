package com.goodjob.post;

import com.goodjob.post.error.PostErrorCode;
import com.goodjob.post.postdto.PostInsertDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PostInsertFormValidator implements ConstraintValidator<PostInsertForm, PostInsertDTO> {


    @Override
    public void initialize(PostInsertForm constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PostInsertDTO value, ConstraintValidatorContext context) {
        System.out.println(value);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = sp.parse(sp.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int invalidCount = 0;
        if(value.getPostOccCode()<0 || value.getPostOccCode()>64){
            addConstraintViolation(context,"Invalid value","postOccCode");
            invalidCount +=1;
        }
        if(value.getPostSalaryId()<0 || value.getPostOccCode()>14){
            addConstraintViolation(context,"Invalid value","postSalaryId");
            invalidCount +=1;
        }
        if(value.getPostOccCode()==0){
            addConstraintViolation(context,"Not Selected","postOccCode");
            invalidCount +=1;
        }
        if(value.getPostSalaryId()==0){
            addConstraintViolation(context,"Not Selected","postSalaryId");
            invalidCount +=1;
        }
        if(!Arrays.asList(new String[]{"남자", "여자","성별무관"}).contains(value.getPostGender())){
            addConstraintViolation(context, "Gender values must be either male, female, or gender-independent","postGender");
            invalidCount += 1;
        }
//        if(value.getPostStartDate().getTime() < nowDate.getTime()){
//            addConstraintViolation(context, "The start date must be after today.","postStartDate");
//            invalidCount += 1;
//        }
//        if(value.getPostEndDate().getTime() < nowDate.getTime()){
//            addConstraintViolation(context, "The end date must be after today.","postEndDate");
//            invalidCount += 1;
//        }
        if(value.getPostStartDate().getTime() > value.getPostEndDate().getTime()){
            addConstraintViolation(context, "The start date must be before the end date.","postStartDate");
            invalidCount += 1;
        }
        if(value.getPostStartDate().getTime() > value.getPostEndDate().getTime()){
            addConstraintViolation(context, "The end date must be after the start date.","postEndDate");
            invalidCount += 1;
        }
        return invalidCount == 0;
    }
    private boolean checkIfNumber(String number){ // true 이면 정수
      return number == null || number.matches("-?\\d+");
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String errorMessage, String firstNode, String secondNode) {
        context.disableDefaultConstraintViolation(); // (4)
        context.buildConstraintViolationWithTemplate(errorMessage) // (5)
                .addPropertyNode(firstNode)
                .addPropertyNode(secondNode)
                .addConstraintViolation();
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String errorMessage, String firstNode) {
        context.disableDefaultConstraintViolation(); // (4)
        context.buildConstraintViolationWithTemplate(errorMessage) // (5)
                .addPropertyNode(firstNode)
                .addConstraintViolation();
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String errorMessage,
                                        String firstNode, String secondNode, String thirdNode) {
        context.disableDefaultConstraintViolation(); // (4)
        context.buildConstraintViolationWithTemplate(errorMessage) // (5)
                .addPropertyNode(firstNode)
                .addPropertyNode(secondNode)
                .addPropertyNode(thirdNode)
                .addConstraintViolation();
    }
}
