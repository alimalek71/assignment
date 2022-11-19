package ir.alimalek.assignment.component;


import ir.alimalek.assignment.domain.Information;
import ir.alimalek.assignment.exception.InvalidCSVInputFile;
import ir.alimalek.assignment.exception.ParsingDateFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InformationCSVComponent {

    private static final String TYPE = "text/csv";
    private static final String[] HEADERS = {
            "source",
            "codeListCode",
            "code",
            "displayValue",
            "longDescription",
            "fromDate",
            "toDate",
            "sortingPriority"
    };

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


    private boolean isCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<Information> csvToInformation(MultipartFile file) {
        if (!isCSVFormat(file)) {
            log.error("File type is not CSV.");
            throw new InvalidCSVInputFile();
        }

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Information> informationList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Date fromDate = !Strings.isBlank(csvRecord.get(HEADERS[5])) ? getDate(csvRecord.get(HEADERS[5])) : null;
                Date toDate = !Strings.isBlank(csvRecord.get(HEADERS[6])) ? getDate(csvRecord.get(HEADERS[6])) : null;
                Integer sortingPriority = !Strings.isBlank(csvRecord.get(HEADERS[7])) ? Integer.valueOf(csvRecord.get(HEADERS[7])) : null;

                Information information = Information.builder()
                        .source(csvRecord.get(HEADERS[0]))
                        .codeListCode(csvRecord.get(HEADERS[1]))
                        .code(csvRecord.get(HEADERS[2]))
                        .displayValue(csvRecord.get(HEADERS[3]))
                        .longDescription(csvRecord.get(HEADERS[4]))
                        .fromDate(fromDate)
                        .toDate(toDate)
                        .sortingPriority(sortingPriority)
                        .build();

                informationList.add(information);
            }

            return informationList;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new InvalidCSVInputFile();
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new ParsingDateFormatException();
        }
    }

    private Date getDate(String date) throws ParseException {
        return new Date(simpleDateFormat.parse(date).getTime());
    }
}
