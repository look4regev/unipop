package org.unipop.elastic.tests;

import org.apache.tinkerpop.gremlin.AbstractGremlinTest;
import org.apache.tinkerpop.gremlin.GraphManager;
import org.apache.tinkerpop.gremlin.LoadGraphWith;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.MapHelper;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.util.Attachable;
import org.apache.tinkerpop.gremlin.structure.util.detached.DetachedEdge;
import org.apache.tinkerpop.gremlin.structure.util.detached.DetachedFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unipop.elastic.ElasticGraphProvider;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.tinkerpop.gremlin.LoadGraphWith.GraphData.GRATEFUL;
import static org.apache.tinkerpop.gremlin.LoadGraphWith.GraphData.MODERN;
import static org.apache.tinkerpop.gremlin.process.traversal.P.without;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource.computer;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.in;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class TemporaryTests extends AbstractGremlinTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractGremlinTest.class);

    public TemporaryTests() throws Exception {
        GraphManager.setGraphProvider(new ElasticGraphProvider());
    }

    @Test
    @LoadGraphWith(MODERN)
    public void test() {
//        Traversal t = g.V().emit().times(2).repeat(out()).path();

        Traversal t = g.V().as("a").in("created").addE("createdBy").from("a").property("year", 2009).property("acl", "public");

//        g.V().emit(__.has("name", "marko").or().loops().is(2)).repeat(__.out()).values("name");


        check(t);
    }

    @Test
    @LoadGraphWith(MODERN)
    public void reduceTest() {
        Traversal t1 = g.V().out().count();
        Traversal t2 = g.V().out().max();
        Traversal t3 = g.V().out().min();
        Traversal t4 = g.V().out().sum();
        Traversal t5 = g.V().out().mean();

        t1.hasNext();
        t2.hasNext();
        t3.hasNext();
        t4.hasNext();
        t5.hasNext();


        System.out.println(t1.next());
        System.out.println(t2.next());
        System.out.println(t3.next());
        System.out.println(t4.next());
        System.out.println(t5.next());
    }

    @Test
    public void nullProperty() {
        graph.addVertex("abc", null);
    }

    private void check(Traversal traversal) {
//        traversal.profile();
        System.out.println("pre-strategy:" + traversal);
        traversal.hasNext();
        System.out.println("post-strategy:" + traversal);

        int count = 0;
        while(traversal.hasNext()) {
            System.out.println(traversal.next());
            count ++;
        }
        System.out.println(count);
    }
}
