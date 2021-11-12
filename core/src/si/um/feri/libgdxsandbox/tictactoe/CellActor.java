package si.um.feri.libgdxsandbox.tictactoe;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class CellActor extends Image {

    private CellState state;

    public CellActor(TextureRegion region) {
        super(region);
        state = CellState.EMPTY;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public void setDrawable(TextureRegion region) {
        super.setDrawable(new TextureRegionDrawable(region));
        addAnimation(); // play animation when region changed
    }

    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }

    private void addAnimation() {
        setOrigin(Align.center);
        addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.rotateBy(720, 0.25f),
                                Actions.scaleTo(0, 0, 0.25f)
                        ),
                        Actions.scaleTo(1, 1, 0.25f)
                )
        );
    }
}
