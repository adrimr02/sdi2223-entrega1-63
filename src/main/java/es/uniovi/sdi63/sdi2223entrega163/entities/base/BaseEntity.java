package es.uniovi.sdi63.sdi2223entrega163.entities.base;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id && version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, version );
    }
}
