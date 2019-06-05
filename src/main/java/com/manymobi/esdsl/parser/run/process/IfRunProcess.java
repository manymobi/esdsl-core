package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.EsdslParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

/**
 * @author 梁建军
 * 创建日期： 2018/11/5
 * 创建时间： 13:55
 * @version 1.0
 * @since 1.0
 * if
 */
public class IfRunProcess extends AbstractRunProcess {

    protected IfRunProcess(List<RunProcess> child) {
        super(child);
    }

    @Override
    public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {

        for (RunProcess runProcess : child) {
            String s = runProcess.runProcess(parameter);
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    public enum LogicalSymbols {
        /**
         * 等于
         */
        EQUAL_TO("=="),
        /**
         * 大于
         */
        GREATER_THAN(">"),
        /**
         * 大于等于
         */
        GREATER_THAN_EQUAL_TO(">="),
        /**
         * 小于
         */
        LESS_THAN("<"),
        /**
         * 小于等于
         */
        LESS_THAN_EQUAL_TO("<="),
        /**
         * 不等于
         */
        NOT_EQUAL_TO("!=", "<>");

        private final String[] symbols;

        LogicalSymbols(String... symbols) {

            this.symbols = symbols;
        }

        public String[] getSymbols() {
            return symbols;
        }

        public static LogicalSymbols get(String symbols) {
            for (LogicalSymbols logicalSymbols : LogicalSymbols.values()) {
                for (String s : logicalSymbols.getSymbols()) {
                    if (Objects.equals(symbols, s)) {
                        return logicalSymbols;
                    }
                }
            }
            return null;
        }
    }

    public enum AndOrXor {
        /**
         * 与
         */
        AND("&", "&&"),
        /**
         * 或
         */
        OR("|", "||"),
        /**
         * 异或
         */
        XOR("^");

        private final String[] symbols;

        AndOrXor(String... symbols) {

            this.symbols = symbols;
        }

        public static AndOrXor get(String symbols) {
            for (AndOrXor logicalSymbols : AndOrXor.values()) {
                for (String s : logicalSymbols.getSymbols()) {
                    if (Objects.equals(symbols, s)) {
                        return logicalSymbols;
                    }
                }
            }
            return null;
        }

        public String[] getSymbols() {
            return symbols;
        }
    }

    public static class Build extends AbstractRunProcess.Build<IfRunProcess> {


        private LinkedList<AbstractRunProcess.Build> builds = new LinkedList<>();

        @Override
        public void addRunProcess(RunProcess runProcess) {
            builds.getLast().addRunProcess(runProcess);
        }

        public Build(EsdslParser.ZifContext child1) {

            If.Build build = new If.Build(child1);
            builds.add(build);
        }

        public void addElseIf(EsdslParser.ZelseifContext child1) {

            If.Build build = new If.Build(child1);
            builds.add(build);
        }

        public void addElse(EsdslParser.ZelseContext child1) {
            Else.Build build = new Else.Build(child1);
            builds.add(build);
        }

        @Override
        public IfRunProcess build() {
            for (AbstractRunProcess.Build build : builds) {
                child.add(build.build());
            }
            return new IfRunProcess(child);
        }
    }

    static class If extends AbstractRunProcess {


        SingleIfCondition[] ifcondition;
        AndOrXor[] andOrXors;

        protected If(List<RunProcess> child, Build build) {
            super(child);
            ifcondition = build.ifcondition.toArray(new SingleIfCondition[build.ifcondition.size()]);
            andOrXors = build.andOrXors.toArray(new AndOrXor[build.andOrXors.size()]);
        }

        @Override
        public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {


            if (!isIfRun(parameter)) {
                return null;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (RunProcess runProcess : child) {
                String s = runProcess.runProcess(parameter);

                if (s != null) {
                    stringBuilder.append(s);
                }
            }
            return stringBuilder.toString();
        }

        private boolean isIfRun(Map<String, Object> parameter) {

            boolean run = false;

            AndOrXor andOrXor = null;

            for (int i = 0; i < ifcondition.length; i++) {

                if (andOrXor == null) {
                    run = ifcondition(parameter, ifcondition[i]);
                } else {
                    boolean temp = ifcondition(parameter, ifcondition[i]);
                    switch (andOrXor) {
                        case AND:
                            run &= temp;
                            break;
                        case OR:
                            run |= temp;
                            break;
                        case XOR:
                            run ^= temp;
                            break;
                        default:
                            throw new RuntimeException("不该走到这里");
                    }
                }


                if (i < andOrXors.length) {
                    andOrXor = andOrXors[i];
                    switch (andOrXor) {
                        case AND:
                            if (!run) {
                                return false;
                            }
                            break;
                        case OR:
                            if (run) {
                                return true;
                            }
                            break;
                        case XOR:
                            break;
                    }
                } else {
                    break;
                }
            }
            return run;
        }

        private boolean ifcondition(Map<String, Object> parameter, SingleIfCondition singleIfCondition) {

            boolean returnB = true;

            boolean valueB = singleIfCondition.valueB;
            String value = singleIfCondition.value;
            boolean value1B = singleIfCondition.value1B;
            String value1 = singleIfCondition.value1;
            LogicalSymbols logicalSymbols = singleIfCondition.logicalSymbols;

            Object valueO = valueB ? getVariable(parameter, value) : value;
            String valueS = valueO != null ? String.valueOf(valueO) : null;
            Object value1O = value1B ? getVariable(parameter, value1) : value1;
            String value1S = value1O != null ? String.valueOf(value1O) : null;
            switch (logicalSymbols) {
                case EQUAL_TO:
                    if (!Objects.equals(valueS, value1S)) {
                        returnB = false;
                    }
                    break;
                case LESS_THAN:
                case LESS_THAN_EQUAL_TO:
                case GREATER_THAN:
                case GREATER_THAN_EQUAL_TO: {
                    Double valueD = Double.parseDouble(valueS);
                    Double value1D = Double.parseDouble(value1S);

                    //下面 不要把“!”去掉，这样写便于理解
                    switch (logicalSymbols) {
                        case LESS_THAN:
                            if (!(valueD < value1D)) {
                                returnB = false;
                            }
                        case LESS_THAN_EQUAL_TO:
                            if (!(valueD <= value1D)) {
                                returnB = false;
                            }
                        case GREATER_THAN:
                            if (!(valueD > value1D)) {
                                returnB = false;
                            }
                        case GREATER_THAN_EQUAL_TO:
                            if (!(valueD >= value1D)) {
                                returnB = false;
                            }
                    }
                    break;
                }
                case NOT_EQUAL_TO:
                    if (Objects.equals(valueS, value1S)) {
                        returnB = false;
                    }
                    break;
            }
            //非
            boolean wrong = singleIfCondition.wrong;
            //当为真的时候 ，取反
            return wrong ? (!returnB) : (returnB);
        }

        public static class Build extends AbstractRunProcess.Build<If> {


            List<SingleIfCondition> ifcondition = new ArrayList<>();
            List<AndOrXor> andOrXors = new ArrayList<>();


            public Build(EsdslParser.ZifContext child1) {
                ifCondition(child1.ifcondition());
            }

            public Build(EsdslParser.ZelseifContext child1) {
                ifCondition(child1.ifcondition());
            }

            private void ifCondition(EsdslParser.IfconditionContext ifconditionContext) {
                for (EsdslParser.SingleIfconditionContext singleIfconditionContext : ifconditionContext.singleIfcondition()) {
                    ifcondition.add(singleIfcondition(singleIfconditionContext));
                }
                for (EsdslParser.And_or_xorContext and_or_xorContext : ifconditionContext.and_or_xor()) {
                    andOrXors.add(AndOrXor.get(and_or_xorContext.getText()));
                }
            }


            private SingleIfCondition singleIfcondition(EsdslParser.SingleIfconditionContext singleIfconditionContext) {

                /**
                 * 非
                 */
                boolean wrong = false;
                boolean valueB = false;
                String value = null;
                boolean value1B = false;
                String value1 = null;
                /**
                 * 逻辑符号
                 */
                String logicalSymbols = null;

                for (ParseTree child : singleIfconditionContext.children) {
                    if (child instanceof EsdslParser.StringContext) {
                        if (logicalSymbols == null) {
                            valueB = false;
                            value = child.getText();
                        } else {
                            value1B = false;
                            value1 = child.getText();
                        }
                    } else if (child instanceof EsdslParser.VariableContext) {
                        if (logicalSymbols == null) {
                            valueB = true;
                            if (child.getChildCount() == 1) {
                                value = null;
                            } else {
                                value = child.getChild(1).getText();
                            }
                        } else {
                            value1B = true;
                            if (child.getChildCount() == 1) {
                                value1 = null;
                            } else {
                                value1 = child.getChild(1).getText();
                            }
                        }
                    } else if (child instanceof EsdslParser.LogicCharacterContext) {
                        logicalSymbols = child.getText();
                    } else if (child instanceof EsdslParser.WrongContext) {
                        wrong = true;
                    }
                }

                return new SingleIfCondition(wrong, valueB, value, value1B, value1, logicalSymbols);
            }

            @Override
            public If build() {
                return new If(child, this);
            }
        }
    }

    private static class SingleIfCondition {
        /**
         * 非
         */
        boolean wrong;
        boolean valueB;
        String value;
        boolean value1B;
        String value1;
        /**
         * 逻辑符号
         */
        LogicalSymbols logicalSymbols;

        public SingleIfCondition(boolean wrong, boolean valueB, String value, boolean value1B, String value1, String logicalSymbols) {
            this.wrong = wrong;
            this.valueB = valueB;
            this.value = value;
            this.value1B = value1B;
            this.value1 = value1;
            this.logicalSymbols = LogicalSymbols.get(logicalSymbols);
            if (logicalSymbols == null) {
                throw new RuntimeException("未知符号");
            }
        }
    }

    private static class Else extends AbstractRunProcess {

        protected Else(List<RunProcess> child) {
            super(child);
        }

        @Override
        public String runProcess(Map<String, Object> parameter) throws IllegalParameterException {
            StringBuilder stringBuilder = new StringBuilder();
            for (RunProcess runProcess : child) {
                String s = runProcess.runProcess(parameter);

                if (s != null) {
                    stringBuilder.append(s);
                }
            }
            return stringBuilder.toString();
        }

        static class Build extends AbstractRunProcess.Build<Else> {

            public Build(EsdslParser.ZelseContext child1) {
            }

            @Override
            public Else build() {
                return new Else(child);
            }
        }
    }
}
