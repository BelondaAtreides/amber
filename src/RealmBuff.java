import haven.Buff;
import haven.Coord;
import haven.Glob;
import haven.Indir;
import haven.ItemInfo;
import haven.Loading;
import haven.Resource;
import haven.RichText;
import haven.Tex;
import haven.TexI;
import haven.Text;
import haven.Widget;
import haven.ItemInfo.ResOwner;
import haven.Resource.Pagina;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class RealmBuff extends Buff implements ResOwner {
    public final Indir<Resource> res;
    public Object[] rawinfo;
    private List<ItemInfo> info = Collections.emptyList();
    private Tex rtip;

    public RealmBuff(Indir<Resource> var1) {
        super(var1);
        this.res = var1;
    }

    public static Widget mkwidget(Widget var0, Object... var1) {
        Indir<Resource> var2 = var0.ui.sess.getres(((Integer) var1[0]).intValue());
        return new RealmBuff(var2);
    }

    public Resource resource() {
        return this.res.get();
    }

    public Glob glob() {
        return this.ui.sess.glob;
    }

    public List<ItemInfo> info() {
        if (this.info == null) {
            this.info = ItemInfo.buildinfo(this, this.rawinfo);
        }

        return this.info;
    }

    public Object tooltip(Coord var1, Widget var2) {
        try {
            if (this.rtip == null) {
                List<ItemInfo> var3 = this.info();
                BufferedImage var4 = ItemInfo.longtip(var3);
                if (var3.size() < 2) {
                    var4 = ItemInfo.catimgs(0, new BufferedImage[]{var4, Text.render(Resource.getLocString(Resource.BUNDLE_LABEL, "This realm confers no bonus.")).img});
                }

                Pagina var5 = this.res.get().layer(Resource.pagina);
                if (var5 != null) {
                    var4 = ItemInfo.catimgs(0, new BufferedImage[]{var4, RichText.render("\n" + Resource.getLocString(Resource.BUNDLE_LABEL, var5.text), 200, new Object[0]).img});
                }

                this.rtip = new TexI(var4);
            }

            return this.rtip;
        } catch (Loading var6) {
            return "...";
        }
    }

    public void uimsg(String var1, Object... var2) {
        if (var1 == "tt") {
            this.rawinfo = var2;
            this.info = null;
            this.rtip = null;
        } else {
            super.uimsg(var1, var2);
        }
    }
}
