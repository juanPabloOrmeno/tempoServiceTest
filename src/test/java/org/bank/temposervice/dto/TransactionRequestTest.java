package org.bank.temposervice.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bank.temposervice.dto.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentRequest Validation Tests")
class TransactionRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debe validar un PaymentRequest válido sin errores")
    void shouldValidateValidPaymentRequestWithoutErrors() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                50000.0,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Debe fallar validación cuando merchantId es null")
    void shouldFailValidationWhenMerchantIdIsNull() {
        // Given
        TransactionRequest request = new TransactionRequest(
                null,
                50000.0,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("MerchantId is required");
    }

    @Test
    @DisplayName("Debe fallar validación cuando merchantId está vacío")
    void shouldFailValidationWhenMerchantIdIsEmpty() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "",
                50000.0,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("MerchantId is required");
    }

    @Test
    @DisplayName("Debe fallar validación cuando amount es null")
    void shouldFailValidationWhenAmountIsNull() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                null,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Amount is required");
    }

    @Test
    @DisplayName("Debe fallar validación cuando amount es cero")
    void shouldFailValidationWhenAmountIsZero() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                0.0,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Amount must be greater than zero");
    }

    @Test
    @DisplayName("Debe fallar validación cuando amount es negativo")
    void shouldFailValidationWhenAmountIsNegative() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                -1000.0,
                "CLP",
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Amount must be greater than zero");
    }

    @Test
    @DisplayName("Debe fallar validación cuando currency es null")
    void shouldFailValidationWhenCurrencyIsNull() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                50000.0,
                null,
                "tok_abc123xyz",
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Currency is required");
    }

    @Test
    @DisplayName("Debe fallar validación cuando cardToken es null")
    void shouldFailValidationWhenCardTokenIsNull() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                50000.0,
                "CLP",
                null,
                "12/26"
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Card token is required");
    }

    @Test
    @DisplayName("Debe fallar validación cuando expirationDate es null")
    void shouldFailValidationWhenExpirationDateIsNull() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "MERCHANT_001",
                50000.0,
                "CLP",
                "tok_abc123xyz",
                null
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Expiration date is required");
    }

    @Test
    @DisplayName("Debe tener múltiples violaciones cuando varios campos son inválidos")
    void shouldHaveMultipleViolationsWhenMultipleFieldsAreInvalid() {
        // Given
        TransactionRequest request = new TransactionRequest(
                "",
                -100.0,
                "",
                "",
                ""
        );

        // When
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(5);
    }

    @Test
    @DisplayName("Debe aceptar diferentes formatos de expirationDate")
    void shouldAcceptDifferentExpirationDateFormats() {
        // Given
        String[] validFormats = {"12/26", "01/25", "06/30"};

        for (String format : validFormats) {
            TransactionRequest request = new TransactionRequest(
                    "MERCHANT_001",
                    50000.0,
                    "CLP",
                    "tok_abc123xyz",
                    format
            );

            // When
            Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

            // Then
            assertThat(violations).isEmpty();
        }
    }

    @Test
    @DisplayName("Debe aceptar diferentes monedas válidas")
    void shouldAcceptDifferentValidCurrencies() {
        // Given
        String[] currencies = {"CLP", "USD", "EUR", "BRL", "MXN"};

        for (String currency : currencies) {
            TransactionRequest request = new TransactionRequest(
                    "MERCHANT_001",
                    1000.0,
                    currency,
                    "tok_abc123xyz",
                    "12/26"
            );

            // When
            Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

            // Then
            assertThat(violations).isEmpty();
        }
    }
}
