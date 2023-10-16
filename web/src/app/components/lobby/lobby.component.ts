import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { SessionService } from "../../services/session.service";
import { WebSocketService } from "../../services/web-socket.service";
import { SessionResponse } from "../../models/session-response";
import { Session } from "../../models/session";
import { Mode } from "../../shared/mode";
import { QuestionSetService } from "../../services/question-set.service";
import { PublicQuestionSetService } from "../../services/public-question-set.service";
import { PublicQuestionSet } from "../../models/public-question-set";
import { QuestionSetResponse } from "../../models/question-set-response";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit, OnDestroy {

  session: Session = {
    host: '',
    code: '',
    active: true,
    mode: Mode.NONE,
    players: []
  };
  userQuestionSets: QuestionSetResponse[] = [];
  publicQuestionSets: PublicQuestionSet[] = [];
  code!: string;
  isHost!: boolean;

  constructor(
    private sessionService: SessionService,
    private questionSetService: QuestionSetService,
    private publicQuestionSetService: PublicQuestionSetService,
    private webSocketService: WebSocketService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.code = this.route.snapshot.paramMap.get('code')!;
    this.fetchSessionData();
    this.fetchQuestionSets();
    this.stompSubscribe();
  }

  ngOnDestroy(): void {
    this.stompUnsubscribe();
  }

  private fetchSessionData(): void {
    this.sessionService.getSession(this.code).subscribe(
      (response: SessionResponse) => {
        this.session = response.session;
        this.isHost = this.session.host === response.username;
      });
  }

  private fetchQuestionSets(): void {
    this.questionSetService.getUserQuestionSets().subscribe(
      (data: QuestionSetResponse[]) => {
        this.userQuestionSets = data;
      });

    this.publicQuestionSetService.getPublicQuestionSets().subscribe(
      (data: PublicQuestionSet[]) => {
        this.publicQuestionSets = data;
      });
  }

  private stompSubscribe(): void {
    this.webSocketService.subscribe('/topic/session/join/' + this.code,
      () => {
        this.fetchSessionData();
      });
    /*this.webSocketService.subscribe('/topic/session/start/' + this.code,
      () => {
        this.router.navigate([`${RouteUrl.GAME}/${this.code}`])
      });*/
  }

  private stompUnsubscribe(): void {
    this.webSocketService.unsubscribe('/topic/session/join/' + this.code);
    //this.webSocketService.unsubscribe('/topic/session/start/' + this.code);
  }
}
