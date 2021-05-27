package mx.nopaloverflow.hybris.testing.dbmappers;

import mx.nopaloverflow.hybris.testing.model.ProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductRowMapper implements RowMapper<ProductDto> {
    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        final var product = new ProductDto();
        product.setCode(rs.getString("p_code"));
        product.setName(rs.getString("p_name"));
        return product;
    }
}
