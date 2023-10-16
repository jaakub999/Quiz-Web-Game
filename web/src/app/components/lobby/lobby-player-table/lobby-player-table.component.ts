import { Component, Input } from '@angular/core';
import { User } from "../../../models/user";

@Component({
  selector: 'app-lobby-player-table',
  templateUrl: './lobby-player-table.component.html'
})
export class LobbyPlayerTableComponent {

  @Input() players!: User[];
}
