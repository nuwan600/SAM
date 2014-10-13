package ejb;

import ejb.BookEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-09T17:37:56")
@StaticMetamodel(AuthorEntity.class)
public class AuthorEntity_ { 

    public static volatile SingularAttribute<AuthorEntity, BookEntity> book;
    public static volatile SingularAttribute<AuthorEntity, String> name;
    public static volatile SingularAttribute<AuthorEntity, Long> id;

}