package Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import Model.Carte;

public class CarteRowMapper implements RowMapper<Carte>{

	@Override
	public Carte mapRow(ResultSet rs, int rowNum) throws SQLException {
		Carte carte = new Carte();
		carte.setId(rs.getInt(1));
		carte.setCodDeBare(rs.getString(2));
		carte.setNume(rs.getString(3));
		carte.setAutor(rs.getString(4));
		carte.setPret(Double.parseDouble(rs.getString(5)));
		carte.setEditura(rs.getString(6));
		return carte;
	}

}
