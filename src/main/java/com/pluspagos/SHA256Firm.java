package com.pluspagos;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Genera la firma del payload del formulario de PlusPagos.
 *
 * <p>Antes se calculaba un SHA-256 simple sobre la concatenación de
 * "datos + secretKey" (hallazgo SEC-03). Esa construcción es vulnerable a
 * ataques de extensión de longitud (length extension), que permitirían a un
 * atacante generar una firma válida para un payload con datos adicionales sin
 * conocer la clave secreta.
 *
 * <p>Ahora se usa HMAC-SHA256 (javax.crypto.Mac), la construcción estándar
 * para autenticar mensajes con una clave secreta: la clave ya no se concatena
 * al texto, sino que se aplica a través del algoritmo HMAC, que es
 * matemáticamente resistente a ese tipo de ataque.
 *
 * <p><b>Los consumidores de esta firma deben compararla usando una función de
 * tiempo constante</b>, como {@link MessageDigest#isEqual(byte[], byte[])}
 * sobre los bytes de la firma (o una implementación equivalente de
 * comparación constante-time sobre el string hexadecimal), en lugar de
 * {@code equals()} o {@code ==}, para evitar ataques de canal lateral
 * (timing attacks) al validar la firma recibida.
 *
 * <p><b>Importante:</b> el valor de la firma calculada con HMAC-SHA256 no es
 * el mismo que el que producía la versión anterior (SHA-256 simple) para los
 * mismos datos de entrada. Cualquier sistema que valide esta firma (por
 * ejemplo, el backend de PlusPagos) debe actualizarse en paralelo.
 */
public class SHA256Firm {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    public String getFirm(String ipClient, String secretKey, String guidComercio, String sucursalId, String monto)
            throws NoSuchAlgorithmException, InvalidKeyException {

        String input = ipClient + "*" + guidComercio + "*" + sucursalId + "*" + monto;

        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
        byte[] hashedBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder output = new StringBuilder(2 * hashedBytes.length);
        for (byte b : hashedBytes) {
            output.append(String.format("%02x", b));
        }
        return output.toString();
    }
}
