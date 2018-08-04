package module;

public class Contact {
    private int mColor;
    private String mName;
    private String mNumber;

    public Contact(int mColor, String mName, String mNumber) {
        this.mColor = mColor;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}
