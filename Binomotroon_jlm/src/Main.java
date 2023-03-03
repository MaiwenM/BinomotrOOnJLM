import modele.DataAccess;
import modele.DataBinomotroon;
import modele.Projets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static modele.Vue.*;

public class Main {
    public static void main(String[] args) {
        se_connecter();
    }
}