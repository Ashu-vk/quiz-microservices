import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'truncate', standalone: true })
export class TruncatePipe implements PipeTransform {
  transform(value: string | null | undefined, length = 50, suffix = '...') {
    if (!value) return '';
    const l = Number(length) || 50;
    if (value.length <= l) return value;
    return value.substring(0, l).trimEnd() + suffix;
  }
}
