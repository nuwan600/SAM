package ejb;

import ejb.AuthorEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-09T17:37:56")
@StaticMetamodel(BookEntity.class)
public class BookEntity_ { 

    public static volatile SingularAttribute<BookEntity, Integer> year_of_publish;
    public static volatile SingularAttribute<BookEntity, Double> price;
    public static volatile SingularAttribute<BookEntity, String> isbn;
    public static volatile SingularAttribute<BookEntity, Long> id;
    public static volatile SingularAttribute<BookEntity, String> title;
    public static volatile SingularAttribute<BookEntity, String> Language_of_book;
    public static volatile SetAttribute<BookEntity, AuthorEntity> authors;

}