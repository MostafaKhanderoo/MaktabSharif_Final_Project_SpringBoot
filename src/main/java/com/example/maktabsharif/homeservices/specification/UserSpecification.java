package com.example.maktabsharif.homeservices.specification;

import com.example.maktabsharif.homeservices.entity.User;
import com.example.maktabsharif.homeservices.enumeration.Operator;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> filterByString(String field, String value) {
        return ((root, query, cb) ->{
            if (field ==null || value ==null){

               return cb.disjunction();
            }
            return cb.like(root.get(field),value);
        }
        );

    }
    public static Specification<User> filterByLong(Long value , Operator operator){
        return ((root, query, cb) ->switch (operator){
            case GT -> cb.greaterThan(root.get("age"),value);
            case LT -> cb.lessThan(root.get("age"),value);
            case EQ -> cb.equal(root.get("age"),value);
            case GTQ -> cb.greaterThanOrEqualTo(root.get("age"),value);
            case LTQ -> cb.lessThanOrEqualTo(root.get("age"),value);

        }
        );
    }
}
