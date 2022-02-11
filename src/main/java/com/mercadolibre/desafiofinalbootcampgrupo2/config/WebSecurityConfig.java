package com.mercadolibre.desafiofinalbootcampgrupo2.config;

import com.mercadolibre.desafiofinalbootcampgrupo2.dao.UserDAO;
import com.mercadolibre.desafiofinalbootcampgrupo2.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDAO repository;

    //autenticacao
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                //inicio - requisito 1
                .antMatchers(HttpMethod.POST, "/fresh-products/inboundorder/").hasAnyAuthority("Representative")
                .antMatchers(HttpMethod.GET, "/fresh-products/inboundorder/*").hasAnyAuthority("Representative")
                .antMatchers(HttpMethod.PUT, "/fresh-products/inboundorder/*").hasAnyAuthority("Representative")
                //fim - requisito 1
                //inicio - requisito 2
                .antMatchers(HttpMethod.GET, "/fresh-products/in-stock").hasAnyAuthority("Representative")
                .antMatchers(HttpMethod.GET, "/fresh-products/in-stock/by-type*").hasAnyAuthority("Representative")
                .antMatchers(HttpMethod.POST, "/fresh-products/orders/").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.GET, "/fresh-products/orders/").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.PUT, "/fresh-products/orders/").hasAnyAuthority("Buyer")
                //fim - requisito 2
                //inicio - requisito 3
                .antMatchers(HttpMethod.GET, "/fresh-products/*").hasAnyAuthority("Representative")
                //fim - requisito 3
                //inicio - requisito 4
                .antMatchers(HttpMethod.GET, "/fresh-products/warehouse/*").hasAnyAuthority("Representative")
                //fim - requisito 4
                //inicio - requisito 5
                .antMatchers(HttpMethod.GET, "/fresh-products/due-date/*").hasAnyAuthority("Representative")
                .antMatchers(HttpMethod.GET, "/fresh-products/due-date/list/*").hasAnyAuthority("Representative")
                //fim - requisito 5
                //inicio - requisito 6 - nathalia
                .antMatchers(HttpMethod.POST, "/fresh-products/returnorders/").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.GET, "/fresh-products/returnorders/*").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.PUT, "/fresh-products/returnorders/*").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.PUT, "/fresh-products/returnorders/cancel/*").hasAnyAuthority("Buyer")
                //inicio - requisito 6 - RODRIGO
                .antMatchers(HttpMethod.GET, "/fleet/*").hasAnyAuthority("Representative")
                //.antMatchers(HttpMethod.GET, "/fresh-products/due-date/list/*").hasAnyAuthority("Representative")
                //fim - requisito 6 - RODRIGO

                //inicio - requisito 6 - Aderson
                .antMatchers(HttpMethod.GET, "/freight/cost/SP").hasAnyAuthority("Buyer")
                .antMatchers(HttpMethod.POST, "/freight/add").hasAnyAuthority("Representative")
//                .antMatchers(HttpMethod.POST, "/user/add/representative").permitAll()
//                .antMatchers(HttpMethod.POST, "/user/add/buyer").permitAll()
//                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                // INICIO - REQ 6 GABRIEL
                .antMatchers("/fresh-products/advertisings/**").hasAnyAuthority("Seller")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationViaTokenFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

    //autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(authenticationService).passwordEncoder(encoder);
    }
}
