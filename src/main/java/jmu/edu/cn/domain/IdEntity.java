package jmu.edu.cn.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/23.
 */
@MappedSuperclass
@DynamicInsert
public class IdEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
