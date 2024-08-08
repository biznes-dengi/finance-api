import {CURRENCY} from '@shared/constants';

export type Goal = {
	name: string;
	savedAmount: number;
	targetAmount: number;
	currencySymbol: string;
};

export type TotalGoal = {
	amount: number;
	currency: CURRENCY;
	currencyCode: string;
	currencySymbol: string;
};
