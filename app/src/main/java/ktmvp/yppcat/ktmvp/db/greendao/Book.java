package ktmvp.yppcat.ktmvp.db.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ypp0623 on 19-3-25.
 */

@Entity
public class Book {
    @Id
    private String id;
    @Property
    private String name;
    @Transient
    private int tempData;
    @Generated(hash = 854128010)
    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1839243756)
    public Book() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
