package telran.java2022.forum.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostGetByPeriodDto {
	LocalDate dateFrom;
	LocalDate dateTo;
}
