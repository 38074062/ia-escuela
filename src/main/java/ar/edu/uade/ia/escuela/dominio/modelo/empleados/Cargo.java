package ar.edu.uade.ia.escuela.dominio.modelo.empleados;

public enum Cargo
{
    DOCENTE( "Docente", 0, "ROLE_DOCENTE" ),
    EMPLEADO( "Empleado", 1, "ROLE_EMPLEADO" ),
    ADMINISTRADOR( "Administrador", 100, "ROLE_ADMINISTRADOR" );

    private String nombre;

    private Integer codigo;

    private String rol;

    private Cargo( String nombre, Integer codigo, String rol )
    {
        this.nombre = nombre;
        this.codigo = codigo;
        this.rol = rol;
    }

    public String getNombre()
    {
        return nombre;
    }

    public Integer getCodigo()
    {
        return codigo;
    }

    public String getRol()
    {
        return rol;
    }

    public static Cargo getCargoPorCodigo( Integer codigo )
    {
        Cargo[] cargos = Cargo.values();
        for ( Cargo cargo : cargos )
        {
            if ( cargo.getCodigo().equals( codigo ) )
            {
                return cargo;
            }
        }
        throw new IllegalArgumentException( "El cargo seleccionado no existe" );
    }

}
