package io.github.udlepsprog2.prog2lib.graphics;

import java.awt.*;

public interface GFillable {
    boolean isFilled();

    void setFilled(boolean filled);

    Color getFillColor();

    void setFillColor(Color fillColor);
}
