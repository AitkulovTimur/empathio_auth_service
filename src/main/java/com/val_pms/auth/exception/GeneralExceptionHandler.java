package com.val_pms.auth.exception;

import com.val_pms.auth.exception.attributes.ApiResponse;
import com.val_pms.auth.exception.attributes.SeverityLevel;
import com.val_pms.auth.jwt.AuthUtils;
import com.val_pms.auth.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @Autowired
    JwtUtils jwtUtils;

    //TODO: use it later to get user id
//    @Autowired
//    JwtUtils jwtUtils;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException exception) {
        return prepareResponse(exception, HttpStatus.NOT_FOUND, SeverityLevel.LOW);
    }

    @ExceptionHandler({
            PasswordMismatchException.class,
            AlreadyExistsException.class
    })
    public ResponseEntity<ApiResponse> handlePasswordMismatchException(PasswordMismatchException exception) {
        return prepareResponse(exception, HttpStatus.CONFLICT, SeverityLevel.LOW);
    }

    @ExceptionHandler(TokenRefreshingException.class)
    public ResponseEntity<ApiResponse> handleTokenRefreshingException(TokenRefreshingException exception) {
        return prepareResponse(exception, HttpStatus.UNAUTHORIZED, SeverityLevel.UNUSUAL);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleRefreshTExpException(RefreshTokenExpiredException exception) {
        return prepareResponse(exception, HttpStatus.UNAUTHORIZED, SeverityLevel.LOW);
    }

    private ResponseEntity<ApiResponse> prepareResponse(Exception exception, HttpStatus status, SeverityLevel severityLevel) {
        ZonedDateTime moment = ZonedDateTime.now();
        String stackTrace = Arrays.toString(exception.getStackTrace());
        String message = exception.getMessage();
        String initiatorId = AuthUtils.getCurrentUserId() != null
                ? AuthUtils.getCurrentUserId().toString()
                : "No info";

        if (!severityLevel.equals(SeverityLevel.LOW))
            logAndSendToBroker(
                    moment,
                    message,
                    stackTrace.length() > 3000 ? stackTrace.substring(0, 3001) : stackTrace,
                    severityLevel,
                    initiatorId
            );

        ApiResponse response = ApiResponse.builder()
                .catchTime(moment)
                //TODO: replace it with JWT utils
                .initiatorId(initiatorId)
                .severityLevel(severityLevel)
                .status(status.toString())
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }


    //TODO: add sending to kafka broker -> message_history service
    private void logAndSendToBroker(ZonedDateTime zonedDateTime, String message,
                                    String stackTrace, SeverityLevel severityLevel, String initiatorId) {
        String logPayLoad = """
                Time: {}
                Caught exception: {}
                Part of the stacktrace: {}
                """;

        if (severityLevel.equals(SeverityLevel.COMMON) || severityLevel.equals(SeverityLevel.UNUSUAL))
            log.warn(logPayLoad, zonedDateTime.toString(), message, stackTrace);

        if (severityLevel.equals(SeverityLevel.HIGH))
            log.error(logPayLoad, zonedDateTime.toString(), message, stackTrace);

        //sending to broker (add to payload initiatorId)

    }
}
