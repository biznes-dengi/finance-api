import {goalApi} from './goal.api.ts';

function useData() {
	const rows = goalApi.fetchList();
	const goalItem = goalApi.fetchItem();

	return {rows, goalItem};
}

export const goalModel = {
	useData,
};
