/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author dell
 */
@Entity
@Table(name="book")
public class BookEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String isbn;
    private double price;
    private int year_of_publish;
    private String Language_of_book;
    @OneToMany(mappedBy="book")
    private Set<AuthorEntity> authors;

    public String getLanguage() {
        return Language_of_book;
    }

    public void setLanguage(String Language) {
        this.Language_of_book = Language;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year_of_publish;
    }

    public void setYear(int year) {
        this.year_of_publish = year;
    }
    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookEntity)) {
            return false;
        }
        BookEntity other = (BookEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.BookEntity[ id=" + id + " ]";
    }
    
}
