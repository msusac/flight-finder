package hr.tvz.susac.java.web.flight.util.converter;

public interface ConverterUtil<E, D>
{
    D convertToDTO(E entity);
    E convertToEntity(D dto);
}