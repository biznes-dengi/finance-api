import {type Goal, type TotalGoal} from './goal.types.ts';
import {CURRENCY} from '@shared/constants';

function fetchList(): Goal[] {
	return [
		{name: 'Mustang', savedAmount: 100, targetAmount: 30000, currencySymbol: '$'},
		{name: 'House', savedAmount: 200, targetAmount: 200000, currencySymbol: 'zł'},
		{name: 'Guitar', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
		{name: 'Maldives', savedAmount: 300, targetAmount: 1500, currencySymbol: 'BYN'},
		{
			name: 'Super poooper trubo extra bagatstwo Gazprom',
			savedAmount: 30000000000,
			targetAmount: 150000000000,
			currencySymbol: 'RUB',
		},
		{name: 'Mac Book 1', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
		{name: 'TV', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
		{name: 'Monitor', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
		{name: 'Airpods', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
		{name: 'Bike', savedAmount: 300, targetAmount: 1500, currencySymbol: '$'},
	];
}

function fetchItem(): TotalGoal {
	return {
		amount: 3000,
		currency: CURRENCY.USD,
		currencyCode: 'USD',
		currencySymbol: '$',
	};
}

export const goalApi = {
	fetchList,
	fetchItem,
};
