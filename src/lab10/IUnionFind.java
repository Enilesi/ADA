
package lab10;


public interface IUnionFind {

    void init(int N);

    int find(int p) ;

    void union(int p, int q) ;

    int count() ;

}
