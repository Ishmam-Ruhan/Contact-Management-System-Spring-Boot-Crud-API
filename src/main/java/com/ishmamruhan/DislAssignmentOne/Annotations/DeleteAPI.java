package com.ishmamruhan.DislAssignmentOne.Annotations;


import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target(value = {ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ResponseMessage
@RequestMapping(method = RequestMethod.DELETE, produces = {"application/json","application/xml"})
public @interface DeleteAPI {

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};
}
