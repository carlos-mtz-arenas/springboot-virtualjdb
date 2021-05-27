package mx.nopaloverflow.hybris.testing.api;

import mx.nopaloverflow.hybris.testing.dbmappers.ProductRowMapper;
import mx.nopaloverflow.hybris.testing.model.ProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class TestEndpoint {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<ProductDto> doSomething() {
        final var products =
                jdbcTemplate.query("select {p.name[en]}, {p.code} from {product as p}",
                        new ProductRowMapper());
        return products;
    }
}
