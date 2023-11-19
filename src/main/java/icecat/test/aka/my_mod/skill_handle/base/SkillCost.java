package icecat.test.aka.my_mod.skill_handle.base;

public class SkillCost {
    public CostType costType;
    public int costValue;
    public CostValueFrom costValueFrom;

    public SkillCost(CostValueFrom eCostValueFrom, int eCostValue) {
        new SkillCost(eCostValueFrom, eCostValue, CostType.Number);
    }

    public SkillCost(CostValueFrom eCostValueFrom, int eCostValue, CostType eCostType) {
        costValueFrom = eCostValueFrom;
        costValue = eCostValue;
        costType = eCostType;
    }

    public enum CostType {
        Number,
        Percentage
    }

    public enum CostValueFrom {
        /**
         * hit point 体力值
         */
        HP,
        /**
         * magic point 法力值
         */
        MP,
        /**
         * stamina 耐力
         */
        STA,
        /**
         * experience point 经验值
         */
        EXP,
    }
}
