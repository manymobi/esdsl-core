package com.manymobi.esdsl.parser.run.process;

import com.manymobi.esdsl.parser.ParamMap;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 梁建军
 * 创建日期： 2021/4/15
 * 创建时间： 下午 9:47
 * @version 1.0
 * @since 1.0
 * 表达式运行处理器
 */
public class ExpressionRunProcess extends AbstractRunProcess {


    public static final String TRUE = "TRUE";

    public static final String FALSE = "FALSE";
    private final SingleIfCondition[] ifCondition;

    private final AndOrXor[] andOrXors;

    protected ExpressionRunProcess(Build build) {
        super(null);
        ifCondition = build.ifCondition.toArray(new SingleIfCondition[0]);
        andOrXors = build.andOrXors.toArray(new AndOrXor[0]);
    }


    @Override
    public String runProcess(ParamMap<String, Object> parameter) {


        if (!isIfRun(parameter)) {
            return FALSE;
        }
        return TRUE;
    }

    private boolean isIfRun(ParamMap<String, Object> parameter) {

        boolean run = false;

        AndOrXor andOrXor = null;

        for (int i = 0; i < ifCondition.length; i++) {

            if (andOrXor == null) {
                run = ifCondition(parameter, ifCondition[i]);
            } else {
                boolean temp = ifCondition(parameter, ifCondition[i]);
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

    private boolean ifCondition(ParamMap<String, Object> parameter, SingleIfCondition singleIfCondition) {
        //返回结果
        boolean returnB = true;
        //逻辑符号
        LogicalSymbols logicalSymbols = singleIfCondition.logicalSymbols;

        String valueS = removeDoubleQuotes(singleIfCondition.value.runProcess(parameter));

        String value1S = removeDoubleQuotes(singleIfCondition.value1.runProcess(parameter));
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
                if (valueS == null) {
                    throw new IllegalParameterException("参数不能为空");
                }
                if (value1S == null) {
                    throw new IllegalParameterException("参数不能为空");
                }
                BigDecimal valueD = new BigDecimal(valueS);
                BigDecimal value1D = new BigDecimal(value1S);

                //下面 不要把“!”去掉，这样写便于理解
                switch (logicalSymbols) {
                    //小于
                    case LESS_THAN:
                        if (valueD.compareTo(value1D) > 0) {
                            returnB = false;
                        }
                        //小于等于
                    case LESS_THAN_EQUAL_TO:
                        if (valueD.compareTo(value1D) >= 0) {
                            returnB = false;
                        }
                        //大于
                    case GREATER_THAN:
                        if (valueD.compareTo(value1D) < 0) {
                            returnB = false;
                        }
                        //大于等于
                    case GREATER_THAN_EQUAL_TO:
                        if (valueD.compareTo(value1D) <= 0) {
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

    /**
     * 当开头结尾都是双引号是，将其去掉
     */
    private static String removeDoubleQuotes(String value) {
        if (value == null) {
            return null;
        }
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }


    public static class SingleIfCondition {
        /**
         * 非
         */
        final boolean wrong;
        /**
         * value是否是变量名
         */
        final RunProcess value;
        /**
         * value1是否是变量名
         */
        final RunProcess value1;
        /**
         * 逻辑符号
         */
        final LogicalSymbols logicalSymbols;


        public SingleIfCondition(boolean wrong, RunProcess value, String logicalSymbols, RunProcess value1) {
            this.wrong = wrong;
            this.value = value;
            this.value1 = value1;
            this.logicalSymbols = LogicalSymbols.get(logicalSymbols);
            if (logicalSymbols == null) {
                throw new RuntimeException("未知符号");
            }
        }


    }


    public static class Build extends AbstractRunProcess.Build<ExpressionRunProcess> {


        private List<SingleIfCondition> ifCondition = new LinkedList<>();

        private List<AndOrXor> andOrXors = new LinkedList<>();

        public void addIfCondition(SingleIfCondition singleIfCondition) {
            ifCondition.add(singleIfCondition);
        }

        public void addAndOrXor(AndOrXor andOrXor) {
            andOrXors.add(andOrXor);
        }

        @Override
        public void addRunProcess(RunProcess runProcess) {
            throw new RuntimeException("不支持");
        }

        @Override
        public ExpressionRunProcess build() {
            return new ExpressionRunProcess(this);
        }
    }
}