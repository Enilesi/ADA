package lab10;



public class WeightedEdge implements Comparable<WeightedEdge>{
    private int either;
    private int other;
    private int w;


    public WeightedEdge(int either, int other, int w) {
        this.either = either;
        this.other = other;
        this.w = w;
    }

    public String toString() {
        return String.format("%d-%d %d", either, other, w);
    }

    public int either() {
        return either;
    }

    public int other(int v) {
        if (v == either)
            return other;
        else
            return either;
    }

    public int weight(){
        return w;
    }


    @Override
    public int compareTo(WeightedEdge that) {
        return Integer.compare(this.w, that.w);
    }

}