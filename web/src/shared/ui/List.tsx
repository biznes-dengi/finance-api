import {ReactNode} from 'react';

import {textHelpers} from '@shared/lib';
import {EmptyState} from '@shared/ui/EmptyState.tsx';
import {APP_TEXT} from '@shared/config';

const {getDontHaveAny} = textHelpers;

type Props<R> = {
	rows: R[];
	renderRow: (row: R) => ReactNode;
};

export function List<R>(props: Props<R>) {
	const {rows, renderRow} = props;

	return (
		<>
			<EmptyState text={getDontHaveAny(APP_TEXT.goal)} visible={!rows.length} />

			{rows.map((row) => {
				return renderRow(row);
			})}
		</>
	);
}
