package com.liteon.renewable_energy.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.liteon.renewable_energy.DTO.GoogleLoginRequest;
import com.liteon.renewable_energy.model.User;
import com.liteon.renewable_energy.repository.UserRepo;
import com.liteon.renewable_energy.util.JwtTokenUtil;
import com.liteon.renewable_energy.util.SecureUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class TokenServices {
    private final String GoogleClientID = "901728295392-76hggfuhf2f28ua73d27pmnbu0rcknic.apps.googleusercontent.com";
    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
            new GsonFactory()
    )
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(GoogleClientID))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Iterable<User> getAllUser() {
        //throw new UnsupportedOperationException("Unsupported Operation.");
        return userRepo.findAll();
    }

    public User obtainToken(User login) {
        try {
            if (Pattern.compile("^(.+)@(\\S+)$").matcher(login.getEmail()).matches() && login.getHash_key() == null) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Request");
            }

            User user = userRepo.findUserByEmailPassword(login.getEmail(), login.getHash_key());
            if (user == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Email or Password entered incorrect.");
            }

            String access_token = jwtTokenUtil.generateToken(login.getEmail());
            String refresh_token = jwtTokenUtil.generateRefreshToken(login.getEmail());

            user.setAccess_token(access_token);
            user.setRefresh_token(refresh_token);
            user.setModified_datetime(new Date());
            return userRepo.save(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public User googlelogin(GoogleLoginRequest req) {
        String email = "";
        boolean emailVerified = false;
        try {
            InputStream is = new URL("https://oauth2.googleapis.com/tokeninfo?access_token=" + req.getAccess_token()).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            System.out.println();
            email = json.getString("email");
            emailVerified = json.getBoolean("email_verified");

        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid ID token.");
        }

        if (!emailVerified) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Google email not vertified.");
        }
        if (!req.getEmail().equals(email)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid token for requested email.");
        }
        try {
            User user = userRepo.findUserByEmail(req.getEmail());

            if (user == null) {
                SecureUtils utils = new SecureUtils();
                user = new User(req.getEmail(),
                        utils.hashSecurePassword(utils.generateSecurePassword(), utils.getSalt()),
                        new Date());
                user.setId(0);
                return obtainToken(userRepo.save(user));
            } else {
                return obtainToken(user);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public User googlelogin2(GoogleLoginRequest req) {
        try {
            GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), req.getAccess_token() + ".");
            Boolean isValidToken = (idToken != null) && verifier.verify(idToken);

            if (isValidToken) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                //String name = (payload.get("name");
                //String pictureUrl = (String) payload.get("picture");
                //String locale = (String) payload.get("locale");
                //String familyName = (String) payload.get("family_name");
                //String givenName = (String) payload.get("given_name");ng) pay

                if (!emailVerified) {
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Google email not vertified.");
                }
                if (!req.getEmail().equals(email)) {
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid token for requested email.");
                }

                User user = userRepo.findUserByEmail(req.getEmail());

                if (user == null) {
                    SecureUtils utils = new SecureUtils();
                    user = new User(req.getEmail(),
                            utils.hashSecurePassword(
                                    //utils.generateSecurePassword()
                                    "Abcd123$"
                                    , utils.getSalt()),
                            new Date());
                    return obtainToken(userRepo.save(user));
                } else {
                    return obtainToken(user);
                }
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid ID token.");
            }
        } catch (IOException | GeneralSecurityException ex) {
            System.out.println(ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid ID token.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public User refreshToken(String token) {
        User user = userRepo.findUserByEmail(jwtTokenUtil.getUsernameFromToken(token));
        boolean validtoken = jwtTokenUtil.validateToken(token, user.getEmail());
        if (user == null || !validtoken)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid refresh token.");

        String access_token = jwtTokenUtil.generateToken(user.getEmail());
        String refresh_token = jwtTokenUtil.generateRefreshToken(user.getEmail());

        user.setAccess_token(access_token);
        user.setRefresh_token(refresh_token);
        user.setModified_datetime(new Date());

        return userRepo.save(user);
    }
}
