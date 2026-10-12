package cl.edusaldo.finanzas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "configuraciones_institucionales")
public class ConfiguracionInstitucional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_establecimiento", nullable = false, length = 150)
    private String nombreEstablecimiento;

    @Column(name = "nombre_centro_padres", nullable = false, length = 150)
    private String nombreCentroPadres;

    @Column(name = "nombre_libreria", nullable = false, length = 150)
    private String nombreLibreria;

    @Column(name = "rut", nullable = false, length = 12)
    private String rut;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "correo_contacto", nullable = false, length = 150)
    private String correoContacto;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "color_principal", length = 20)
    private String colorPrincipal = "#1F4E79";

    @Column(name = "color_acento", length = 20)
    private String colorAcento = "#D9E1F2";

    public ConfiguracionInstitucional() {}

    public Long getId() { return id; }
    public String getNombreEstablecimiento() { return nombreEstablecimiento; }
    public String getNombreCentroPadres() { return nombreCentroPadres; }
    public String getNombreLibreria() { return nombreLibreria; }
    public String getRut() { return rut; }
    public String getDireccion() { return direccion; }
    public String getCorreoContacto() { return correoContacto; }
    public String getLogoUrl() { return logoUrl; }
    public String getColorPrincipal() { return colorPrincipal; }
    public String getColorAcento() { return colorAcento; }
}
