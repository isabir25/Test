
	<div class="container">
		<g:each in="${getAnswers}" var="answer">
			<div class="row">
				<div class="col-lg-1">
					<ul class="cat">
						<li><i class="icon-angle-right"></i>
							<g:formatDate format="yy-MM-dd" date="${answer?.createDate}"/>									
						</li>
					</ul>
				</div>				
				<div class="col-lg-3">					
					<div class="widget">
						<ul class="recent">
							<li>
								<img src="${resource(dir:'images/photos',file:'thumb1.jpg')}" class="pull-left" alt="" />
									
								<p>
									<g:message code="${answer?.question?.asker?.firstName} 
													${answer?.question?.asker?.lastName}"/><br>
									<code><g:message code="${answer?.question?.asker?.reputation?.value} "/></code> 
									badges ??
								</p>
							</li>
						</ul>
					</div>
				</div>				
				<div class="col-lg-8">
					<h5 class="widgetheading">			
						<ul class="cat">
							<li><i class="icon-angle-right"></i>
								<g:link controller="question" action="show" params="[identifier: answer?.question?.id]">
									${answer?.question?.title}
								</g:link>
							</li>
						</ul>
					</h5>
				</div>	
			</div>	
			
		</g:each>
	</div>
