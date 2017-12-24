public class Filter {
    public final int type;

    public Filter(int type){
        this.type = type;
    }

    public String Apply(String src, boolean current_state, boolean former_state){
        String res = src;

        if (type == 1){
            String[] res_a = src.split("_P");
            if (res_a[1].charAt(0) == '1')
                res = "DETECTE";
            else
                res = "NA";
            return res;
        }

        if (type == 2){
            if (current_state != former_state)
                return res;
        }

        return "";
    }
}
