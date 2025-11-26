package dasturlash.uz.mapper;

import jakarta.persistence.criteria.CriteriaBuilder;

public interface RegionMapper {

    Integer getId();
    String getName();
    Integer getOrderNumber();
    String getRegionKey();

}
