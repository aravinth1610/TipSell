package org.tipSell.eSecurity.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.entity.Attribute;
import org.tipSell.eSecurity.payload.request.AttributeRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T14:40:02+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class AttributeMapperImpl extends AttributeMapper {

    @Override
    public Attribute attribute(AttributeRequest attributeRequest, Long clientUid) {
        if ( attributeRequest == null && clientUid == null ) {
            return null;
        }

        Attribute attribute = new Attribute();

        attribute.setClient( mapClient(clientUid) );

        return attribute;
    }
}
