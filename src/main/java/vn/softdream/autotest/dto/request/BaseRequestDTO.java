package vn.softdream.autotest.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Contains common information for all requests
 * @author thoaidc
 */
@SuppressWarnings("unused")
public class BaseRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer page;
    private Integer size;
    private String sort;

    public Pageable getPageable() {
        if (page == null || size == null || page < 0 || size <= 0) {
            return Pageable.unpaged();
        }

        if (StringUtils.hasText(sort)) {
            String[] sortArray = sort.split(",");
            Sort.Direction sortDirection = Sort.Direction.ASC;

            if (sortArray.length > 1) {
                String sortField = sortArray[0].trim();
                String direction = sortArray[1].trim();

                if (Objects.equals(direction, "desc")) {
                    sortDirection = Sort.Direction.DESC;
                }

                if (StringUtils.hasText(sortField)) {
                    return PageRequest.of(page, size, Sort.by(sortDirection, sortField));
                }
            }
        }

        return PageRequest.of(page, size);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
