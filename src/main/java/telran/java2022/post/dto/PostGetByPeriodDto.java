package telran.java2022.post.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostGetByPeriodDto {
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateFrom;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateTo;
}
