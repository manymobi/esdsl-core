package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.ParamMap;

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


    private final RunProcess elseRunProcess;

    protected IfRunProcess(IfRunProcess.Build build) {
        super(build.ifRunProcesses.toArray(new RunProcess[0]));
        elseRunProcess = build.elseRunProcess;
    }

    @Override
    public String runProcess(ParamMap<String, Object> parameter) throws IllegalParameterException {
        for (RunProcess ifRunProcess : child) {
            String s = ifRunProcess.runProcess(parameter);
            if (s != null) {
                return s;
            }
        }

        return Optional.ofNullable(elseRunProcess)
                .map(runProcess -> runProcess.runProcess(parameter))
                .orElse("");
    }


    public static class Build extends AbstractRunProcess.Build<IfRunProcess> {


        private List<IfRunProcess.If> ifRunProcesses = new LinkedList<>();

        private RunProcess elseRunProcess;


        @Override
        public void addRunProcess(RunProcess runProcess) {
            throw new RuntimeException("不支持");
        }

        public Build() {
        }

        public void addIfOrElseIf(IfRunProcess.If ifRunProcess) {
            ifRunProcesses.add(ifRunProcess);
        }

        public void setElse(RunProcess runProcess) {
            elseRunProcess = runProcess;
        }

        @Override
        public IfRunProcess build() {
            return new IfRunProcess(this);
        }
    }

    public static class If extends AbstractRunProcess {

        private final ExpressionRunProcess expressionRunProcess;

        private final RunProcess statement;

        public If(Build build) {
            super(null);
            expressionRunProcess = build.expression;
            statement = build.statement;
        }

        @Override
        public String runProcess(ParamMap<String, Object> parameter) throws IllegalParameterException {
            String s = expressionRunProcess.runProcess(parameter);
            if (ExpressionRunProcess.FALSE.equals(s)) {
                return null;
            }

            return statement.runProcess(parameter);
        }

        public static class Build extends AbstractRunProcess.Build<If> {

            private ExpressionRunProcess expression;

            private RunProcess statement;

            public void setExpression(ExpressionRunProcess expression) {
                this.expression = expression;
            }


            public void setStatement(RunProcess statement) {
                this.statement = statement;
            }

            //            private SingleIfCondition singleIfcondition(EsdslParser.SingleIfconditionContext singleIfconditionContext) {
//
//                /**
//                 * 非
//                 */
//                boolean wrong = false;
//                boolean valueB = false;
//                String value = null;
//                boolean value1B = false;
//                String value1 = null;
//                /**
//                 * 逻辑符号
//                 */
//                String logicalSymbols = null;
//
//                for (ParseTree child : singleIfconditionContext.children) {
//                    if (child instanceof EsdslParser.StringContext) {
//                        if (logicalSymbols == null) {
//                            valueB = false;
//                            value = child.getText();
//                        } else {
//                            value1B = false;
//                            value1 = child.getText();
//                        }
//                    } else if (child instanceof EsdslParser.VariableContext) {
//                        if (logicalSymbols == null) {
//                            valueB = true;
//                            if (child.getChildCount() == 1) {
//                                value = null;
//                            } else {
//                                value = child.getChild(1).getText();
//                            }
//                        } else {
//                            value1B = true;
//                            if (child.getChildCount() == 1) {
//                                value1 = null;
//                            } else {
//                                value1 = child.getChild(1).getText();
//                            }
//                        }
//                    } else if (child instanceof EsdslParser.LogicCharacterContext) {
//                        logicalSymbols = child.getText();
//                    } else if (child instanceof EsdslParser.WrongContext) {
//                        wrong = true;
//                    }
//                }
//
//                return new SingleIfCondition(wrong, valueB, value, value1B, value1, logicalSymbols);
//            }

            @Override
            public If build() {
                return new If(this);
            }
        }
    }
}
